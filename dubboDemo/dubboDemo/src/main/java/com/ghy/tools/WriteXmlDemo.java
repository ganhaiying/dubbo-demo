package com.ghy.tools;

import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class WriteXmlDemo {
    public static void main(String[] args) throws Exception{
        List<Pom> pomList = new ArrayList<Pom>();
        pomList.add(new Pom("com.alibaba","QLExpress","3.2.0","pom"));

        /*
         * 写出XML文档的大致步骤
         * 1.创建空白文档对象Document
         * 2.向该文档中添加根元素
         * 3.按照规定的XML文档结构从根元素开始，逐级添加子元素，已完成该结构
         * 4.创建XMLWriter
         * 5.将Document对象写出成XML文档
         * 6.将XMLWriter关闭
         */
        //1
//        Document doc = DocumentHelper.createDocument();
        SAXReader reader = new SAXReader();
        String consumerPom = System.getProperty("user.dir") + File.separator + "dubbo_consumer" + File.separator + "pom.xml";
        Document doc = reader.read(new FileInputStream(consumerPom));
        /*
         * 2.Element addElement(String name)
         * Document提供的该方法是用来向当前文档中添加给定名字的根元素。并将其以一个Element
         * 实例返回以便于继续对该根元素操作。
         * 需要注意，该方法只能调用一次，因为一个文档中只能有一个根元素。
         */
        Element root = doc.getRootElement();
        //将所有员工信息以若干<emp>标签形式添加到<list>中
        for(Pom pom:pomList){
            /*
             * Element提供了向其中添加子标签的方法:
             * Element addElement(String name)
             */
            Element pomEle = root.addElement("dependency");
            //添加<groupIdEle>标签
            Element groupIdEle = pomEle.addElement("groupId");
            groupIdEle.addText(pom.getGroupId());
            //添加<artifactId>标签
            Element artifactIdEle = pomEle.addElement("artifactId");
            artifactIdEle.addText(pom.getArtifactId());
            //添加<version>标签
            Element versionEle = pomEle.addElement("version");
            versionEle.addText(pom.getVersion());
            //添加<scope>标签
            Element scopeEle = pomEle.addElement("scope");
            scopeEle.addText(pom.getScope());
        }
        try {
            FileOutputStream fos = new FileOutputStream("pom.xml");
            XMLWriter writer = new XMLWriter(fos,OutputFormat.createCompactFormat());
            writer.write(doc);
            System.out.println("写出完毕!");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
