package com.ghy.tools;

import org.apache.maven.shared.invoker.*;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParseXMLDemo {
    public static void main(String[] args) {
        List<Element> dependencieList = new ArrayList<>();
        List<Pom> newDependencyList = new ArrayList<Pom>();
        newDependencyList.add(new Pom("com.alibaba","QLExpress","3.2.0","pom"));
        /*
         * 读取emplist文件，将该xml文档中的所有员工信息解析出来，
         * 并以若干Emp实例保存，然后将这些员工信息存入到一个List集合中。
         */
        /*
         * 使用DOM解析XML的四大致流程：
         * 1.创建SAXReader
         * 2.使用SAXReader读取要解析的XML文档，该步骤就是DOM耗时耗资源的地方，
         * 因为会将文档所有内容解析完毕并存入到内存中。
         * 读取方法会返回一个Document对象，该对象就表示解析出来的该XML文档内容
         * 3.通过Document对象获取根元素（根标签）。
         * 4.根据文档的结构，从根元素开始逐级获取子元素以达到遍历XML文档数据的目的。
         */
        try {
            //1
            SAXReader reader = new SAXReader();

            //2
            String consumerPom = System.getProperty("user.dir") + File.separator + "dubbo_consumer" + File.separator + "pom.xml";
            Document doc = reader.read(new FileInputStream(consumerPom));
            /*
             *3.获取根元素
             *Element的每一个实例用于表示XML文档中的一个元素（一对标签）
             *它提供了很多用于操作当前标签的方法，其中常用的用于获取标签信息的方法有：
             *
             * String getName()  获取标签名
             *
             * Element elements(Sting name) 根据给定名字获取当前标签中的子标签
             *
             * List elements()
             * 获取当前标签中的所有子标签，返回的集合中是若干Element实例，每个为一个子元素
             *
             * List elements(String name)  获取当前标签中的所有同名名字元素
             *
             * String getText()  获取当前标签中间的文本
             *
             * Attribute attribute(String name)  获取当前标签中指定名字的属性
             *
             */
            Element root = doc.getRootElement();
            List<Pom> dependencyList = new ArrayList<Pom>();
            /*
             * 获取<list>标签中的所有<emp>标签
             */
            List<Element>  list = root.elements();
            /*
             * 将每一个<emp>标签中的内容取到，并以一个Emp实例保存，然后将该对象存入集合
             */
            for(Element pomEle : list){
                if("dependencies".equals(pomEle.getQualifiedName())){
                    dependencieList = pomEle.elements("dependency");
                    for(Element dependency : dependencieList){
                        //获取groupId
                        Element groupIdEle = dependency.element("groupId");
                        String groupId  = groupIdEle.getText();
                        //获取artifactId
                        String artifactId = dependency.elementText("artifactId");
                        System.out.println(artifactId);
                        //获取version
                        String version = dependency.elementText("version");
                        //获取scope
                        String scope = dependency.elementText("scope");
                        Pom pom = new Pom(groupId, artifactId, version, scope);
                        dependencyList.add(pom);
                    }
                }
            }
            Element dependencies = root.element("dependencies");
            for(Pom pom : newDependencyList){
                /*
                 * Element提供了向其中添加子标签的方法:
                 * Element addElement(String name)
                 */
                Element dependency = dependencies.addElement("dependency");
                //添加<groupIdEle>标签
                Element groupIdEle = dependency.addElement("groupId");
                groupIdEle.addText(pom.getGroupId());
                //添加<artifactId>标签
                Element artifactIdEle = dependency.addElement("artifactId");
                artifactIdEle.addText(pom.getArtifactId());
                //添加<version>标签
                Element versionEle = dependency.addElement("version");
                versionEle.addText(pom.getVersion());
                //添加<scope>标签
                Element scopeEle = dependency.addElement("scope");
                scopeEle.addText(pom.getScope());

                if(!dependencieList.contains(dependency)){
                    try {
                        FileOutputStream fos = new FileOutputStream(consumerPom);
                        OutputFormat outputFormat = OutputFormat.createCompactFormat();


                        //设置XML文档的编码类型
                        outputFormat.setEncoding("UTF-8");
                        //设置是否换行
                        outputFormat.setNewlines(true);
                        outputFormat.setSuppressDeclaration(true);
                        //设置是否缩进
                        outputFormat.setIndent(true);
                        //以空格方式实现缩进
                        outputFormat.setIndent("    ");
                        XMLWriter writer = new XMLWriter(fos,outputFormat);
                        writer.write(doc);
                        System.out.println("写出完毕!");
                        writer.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            //更新maven
            InvocationRequest request = new DefaultInvocationRequest();
            request.setPomFile(new File(consumerPom));
            request.setGoals(Collections.singletonList("compile"));
            Invoker invoker = new DefaultInvoker();
            InvocationResult result = invoker.execute(request);
            if(result.getExitCode() == 0){
//                return "修改pom成功！更新maven成功！";
                System.out.println("修改pom成功！更新maven成功！");
            }else{
//                return "修改pom成功！更新maven失败！";
                System.out.println("修改pom成功！更新maven失败！");
            }
            System.out.println("解析完成!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
