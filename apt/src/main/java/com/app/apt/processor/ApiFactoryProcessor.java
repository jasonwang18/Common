package com.app.apt.processor;

import com.app.annotation.apt.ApiFactory;
import com.app.annotation.apt.ContractFactory;
import com.app.apt.AnnotationProcessor;
import com.app.apt.inter.IProcessor;
import com.app.apt.util.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.FilerException;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

import static com.squareup.javapoet.TypeSpec.classBuilder;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * Created by wangshizhan on 17/07/28.
 * 2.7.6版本为单module
 * 3.0以后版本支持多module
 * 接口路由器
 */

public class ApiFactoryProcessor implements IProcessor {
    @Override
    public void process(RoundEnvironment roundEnv, AnnotationProcessor mAbstractProcessor) {
        String CLASS_NAME = null;
        TypeSpec.Builder tb = null;
        String CLIENT_PATH = null;
        try {
            for (TypeElement element : ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(ApiFactory.class))) {
                mAbstractProcessor.mMessager.printMessage(Diagnostic.Kind.NOTE, "正在处理: " + element.toString());

                if(CLASS_NAME == null){
                    CLASS_NAME = element.getAnnotation(ApiFactory.class).name();

                    if("".equals(CLASS_NAME)){
                        CLASS_NAME = "HttpClient";
                    }
                    tb = classBuilder(CLASS_NAME).addModifiers(PUBLIC, FINAL).addJavadoc("@API factory created by apt\n");
                }

                if(CLIENT_PATH == null) {
//                    String temp = element.getQualifiedName().toString();
//                    temp = temp.substring(0, temp.lastIndexOf("."));
//                    String PACKAGE_NAME = temp.substring(0, temp.lastIndexOf("."));
//                    CLIENT_PATH = PACKAGE_NAME + ".network";
                    CLIENT_PATH = element.getEnclosingElement().toString();
                }

                for (Element e : element.getEnclosedElements()) {
                    ExecutableElement executableElement = (ExecutableElement) e;
                    MethodSpec.Builder methodBuilder =
                            MethodSpec.methodBuilder(e.getSimpleName().toString())
                                    .addJavadoc("@created by apt\n")
                                    .addModifiers(PUBLIC, STATIC);

                    methodBuilder.returns(TypeName.get(executableElement.getReturnType()));
                    String paramsString = "";
                    for (VariableElement ep : executableElement.getParameters()) {
                        methodBuilder.addParameter(TypeName.get(ep.asType()), ep.getSimpleName().toString());
                        paramsString += ep.getSimpleName().toString() + ",";
                    }

//                    String[] names = element.getQualifiedName().toString().split("\\.");
//                    StringBuilder sb = new StringBuilder();
//                    for(int i =0; i< names.length-1;i++){
//
//                        sb.append(names[i]);
//
//                        if(i != names.length -2)
//                            sb.append(".");
//
//                    }


                    if("".equals(paramsString)){
                        methodBuilder.addStatement(
                                "return $T.getInstance()" +
                                        ".retrofit.create($T.class).$L()" +
                                        ".compose($T.io_main())"
                                , ClassName.get("com.supcon.mes.mbap.network", "Api")
//                                , element.getQualifiedName()
                                , ClassName.get(element)
                                , e.getSimpleName().toString()
                                , ClassName.get("com.supcon.common.com_http.util", "RxSchedulers"));
                        tb.addMethod(methodBuilder.build());

                    }
                    else {
                        methodBuilder.addStatement(
                                "return $T.getInstance()" +
                                        ".retrofit.create($T.class).$L($L)" +
                                        ".compose($T.io_main())"
                                , ClassName.get("com.supcon.mes.mbap.network", "Api")
//                                , element.getQualifiedName()
                                , ClassName.get(element)
                                , e.getSimpleName().toString()
                                , paramsString.substring(0, paramsString.length() - 1)
                                , ClassName.get("com.supcon.common.com_http.util", "RxSchedulers"));
                        tb.addMethod(methodBuilder.build());
                    }

                }
            }

            if(CLIENT_PATH == null){
                return;
            }
            JavaFile javaFile = JavaFile.builder(CLIENT_PATH, tb.build()).build();// 生成源代码
            javaFile.writeTo(mAbstractProcessor.mFiler);// 在 app module/build/generated/source/apt 生成一份源代码
        } catch (FilerException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
