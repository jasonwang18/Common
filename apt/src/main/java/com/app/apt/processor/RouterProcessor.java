package com.app.apt.processor;

import com.app.annotation.apt.Router;
import com.app.apt.AnnotationProcessor;
import com.app.apt.helper.RouterActivityModel;
import com.app.apt.inter.IProcessor;
import com.app.apt.util.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.FilerException;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

import static com.squareup.javapoet.TypeSpec.classBuilder;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * Created by wangshizhan on 17/07/28.
 * 2.7.6版本为单module
 * 3.0以后版本支持多module
 * 视图跳转路由器
 */

public class RouterProcessor implements IProcessor {
    @Override
    public void process(RoundEnvironment roundEnv, AnnotationProcessor mAbstractProcessor) {
        String CLASS_NAME = "IntentRouter";
        TypeSpec.Builder tb = classBuilder(CLASS_NAME)
                .addModifiers(PUBLIC, FINAL)
                .addSuperinterface(ClassName.get("com.supcon.common.com_router.api","IRouter"))
                .addJavadoc("@API intent router created by apt\n");

        MethodSpec.Builder methodBuilder1 = MethodSpec.methodBuilder("go")
                .addJavadoc("@created by apt \n")
                .addModifiers(PUBLIC, STATIC)
                .addParameter(ClassName.get("android.content", "Context"), "context")
                .addParameter(String.class, "name")
                .addParameter(ClassName.get("android.os", "Bundle"), "extra");

        List<ClassName> mList = new ArrayList<>();
        CodeBlock.Builder blockBuilderGo = CodeBlock.builder();
        ClassName mIntentClassName = ClassName.get("android.content", "Intent");

        blockBuilderGo.add("$L intent =" +//2
                        "new $L();\n", //7
                mIntentClassName,
                mIntentClassName
        );

        blockBuilderGo.add("if(extra != null)\n");
        blockBuilderGo.add("\t\tintent.putExtras(extra);\n");
        blockBuilderGo.beginControlFlow(" switch (name)");//括号开始

        List<RouterActivityModel> mRouterActivityModels = new ArrayList<>();
        try {
            for (TypeElement element : ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(Router.class))) {
                ClassName currentType = ClassName.get(element);
                if (mList.contains(currentType)) continue;
                mList.add(currentType);
                RouterActivityModel mRouterActivityModel = new RouterActivityModel();
                mRouterActivityModel.setElement(element);
                mRouterActivityModel.setActionName(element.getAnnotation(Router.class).value());

                mRouterActivityModel.setNeedBind(false);
                mRouterActivityModels.add(mRouterActivityModel);
            }

            for (RouterActivityModel item : mRouterActivityModels) {
                blockBuilderGo.add("case $S: \n", item.getActionName());//1

                blockBuilderGo.add("intent.setClass(context, $L.class);\n", item.getElement());

                blockBuilderGo.addStatement("\nbreak");//1
            }

            blockBuilderGo.addStatement("default: \n" +
                    "try{\n" +
                    "Class<?> clazz = Class.forName(name);\n"+
                    "intent.setClass(context, clazz);\n"+
                    "}catch(Exception e){}"+
                    "break");

            blockBuilderGo.endControlFlow();
            blockBuilderGo.add("context.startActivity(" +//2
                    "intent" +//3
                    ");\n"//4
            );
            methodBuilder1.addCode(blockBuilderGo.build());

            tb.addMethod(methodBuilder1.build());

            tb.addMethod(MethodSpec.methodBuilder("go")
                    .addJavadoc("@created by apt")
                    .addModifiers(PUBLIC, STATIC)
                    .addParameter(ClassName.get("android.content", "Context"), "context")
                    .addParameter(String.class, "name")
                    .addCode("go(context, name, null);\n").build());

            JavaFile javaFile = JavaFile.builder(Utils.PackageName, tb.build()).build();// 生成源代码
            javaFile.writeTo(mAbstractProcessor.mFiler);// 在 app module/build/generated/source/apt 生成一份源代码
        } catch (FilerException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
