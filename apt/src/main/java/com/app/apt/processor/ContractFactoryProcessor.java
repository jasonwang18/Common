package com.app.apt.processor;

import com.app.annotation.apt.ContractFactory;
import com.app.apt.AnnotationProcessor;
import com.app.apt.inter.IProcessor;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.FilerException;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

import static com.squareup.javapoet.TypeSpec.classBuilder;
import static com.squareup.javapoet.TypeSpec.interfaceBuilder;
import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * Created by wangshizhan on 17/07/28.
 * 2.7.6版本为单module
 * 3.0以后版本支持多module
 * 接口路由器
 */

public class ContractFactoryProcessor implements IProcessor {
    @Override
    public void process(RoundEnvironment roundEnv, AnnotationProcessor mAbstractProcessor) {
        try {
            for (TypeElement element : ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(ContractFactory.class))) {
                mAbstractProcessor.mMessager.printMessage(Diagnostic.Kind.NOTE, "正在处理: " + element.toString());
                String CLASS_NAME = element.getSimpleName().toString().replace("API","Contract");

                String temp  = element.getQualifiedName().toString();
                temp = temp.substring(0, temp.lastIndexOf("."));
                String PACKAGE_NAME = temp.substring(0, temp.lastIndexOf("."));
                String CONTRACT_PATH = PACKAGE_NAME+".contract";
                String BEAN_PATH = PACKAGE_NAME+".bean";
                String API_PATH = PACKAGE_NAME+".api";

                mAbstractProcessor.mMessager.printMessage(Diagnostic.Kind.NOTE, "生成Contract: " + CLASS_NAME);
                mAbstractProcessor.mMessager.printMessage(Diagnostic.Kind.NOTE, "包名: " + PACKAGE_NAME);

//                AnnotationValue annotationValue = null;
//                for( AnnotationMirror am : element.getAnnotationMirrors() ){
//                    if(ContractFactory.class.getName().equals(am.getAnnotationType().toString()))
//                    {
//                        for( Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : am.getElementValues().entrySet() )
//                        {
//                            if( "entites".equals(
//                                entry.getKey().getSimpleName().toString() ) )
//                            {
//                                annotationValue = entry.getValue();
//                                break;
//                            }
//                        }
//                    }
//                }

                List<String> entityClass = null;

//                if(annotationValue!=null){
//                    entityClass = (List<String>)annotationValue.getValue();
//                }




                TypeSpec.Builder tb = interfaceBuilder(CLASS_NAME).addModifiers(PUBLIC, STATIC)
                        .addJavadoc("@Contract created by apt\n"+
                        "注解内实体和方法是一一对应的\n" +
                        "add by wangshizhan\n");
                TypeSpec.Builder viewTb =
                        interfaceBuilder("View")
                                .addModifiers(PUBLIC, STATIC)
                                .addSuperinterface(ClassName.get("com.supcon.common.view.contract","IBaseView"))
                                .addJavadoc("@View created by apt\n");

                entityClass = Arrays.asList(element.getAnnotation(ContractFactory.class).entites());

                List<Element> enclosedMethods = new ArrayList<>();
//                enclosedMethods.addAll(element.getSuperclass().getEnclosedElements());
                enclosedMethods.addAll(element.getEnclosedElements());

                int index = 0;
                for (Element e : enclosedMethods) {
                    ExecutableElement executableElement = (ExecutableElement) e;
                    MethodSpec.Builder successMethodBuilder = null;

                    if( (entityClass.size() >= index+1) && !"".equals(entityClass.get(index)) ) {

                        String entityName = entityClass.get(index);

                        boolean isFullPath = entityName.contains(".");

                        ClassName entityClassName = null;

                        if(isFullPath){
                            entityClassName = ClassName.get(entityName.substring(0, entityName.lastIndexOf(".")), entityName.substring(entityName.lastIndexOf(".")+1, entityName.length()));
                        }
                        else{
                            entityClassName = ClassName.get(BEAN_PATH, entityName);
                        }

                        successMethodBuilder =
                                MethodSpec.methodBuilder(e.getSimpleName().toString()+"Success")
                                        .addParameter(entityClassName, "entity")
                                        .addJavadoc("@method create by apt\n")
                                        .addModifiers(PUBLIC, ABSTRACT);
                    }
                    else {
                        successMethodBuilder =
                                MethodSpec.methodBuilder(e.getSimpleName().toString() + "Success")
                                        .addJavadoc("@method create by apt\n")
                                        .addModifiers(PUBLIC, ABSTRACT);
                    }


                    successMethodBuilder.returns(TypeName.get(executableElement.getReturnType()));

                    MethodSpec.Builder failedMethodBuilder =
                            MethodSpec.methodBuilder(e.getSimpleName().toString()+"Failed")
                                    .addParameter(ClassName.get(String.class), "errorMsg")
                                    .addJavadoc("@method create by apt\n")
                                    .addModifiers(PUBLIC, ABSTRACT);

                    failedMethodBuilder.returns(TypeName.get(executableElement.getReturnType()));

                    viewTb.addMethod(successMethodBuilder.build());
                    viewTb.addMethod(failedMethodBuilder.build());
                    index++;
                }

                tb.addType(viewTb.build());

                TypeSpec.Builder presenterTb =
                        classBuilder("Presenter")
                                .addModifiers(PUBLIC, STATIC, ABSTRACT)
                                .superclass(ParameterizedTypeName.get(ClassName.get("com.supcon.common.view.base.presenter","BasePresenter"),
                                        ClassName.get(CONTRACT_PATH,CLASS_NAME+".View")))
                                .addSuperinterface(ClassName.get(API_PATH, element.getSimpleName().toString()))
                                .addJavadoc("@Presenter created by apt\n");

                tb.addType(presenterTb.build());

                if(CONTRACT_PATH == null){
                    return;
                }

                JavaFile javaFile = JavaFile.builder(CONTRACT_PATH, tb.build()).build();// 生成源代码
                javaFile.writeTo(mAbstractProcessor.mFiler);// 在 app module/build/generated/source/apt 生成一份源代码
            }

        } catch (FilerException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
