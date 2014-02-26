/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */
package com.cisco.oss.foundation.monitoring;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
 */
public final class DOMSupport {
    private static DocumentBuilder db = null;

    static {
        try {
            db = getNewDocumentBuilder();
        } catch (ParserConfigurationException e) {
            db = null;
        }
    }

    private DOMSupport() {
    }

    /**
     * DOCUMENT ME!
     *
     * @param uri DOCUMENT ME!
     * @return DOCUMENT ME!
     * @throws javax.xml.parsers.ParserConfigurationException DOCUMENT ME!
     * @throws org.xml.sax.SAXException                       DOCUMENT ME!
     * @throws java.io.IOException                            DOCUMENT ME!
     */
    public static Document getDocumentFromFile(String uri) throws ParserConfigurationException, SAXException,
            IOException {
        Document doc = DOMSupport.db.parse(new File(uri));
        return doc;
    }

    private static DocumentBuilder getNewDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        return db;
    }

    /**
     * DOCUMENT ME!
     *
     * @param xml DOCUMENT ME!
     * @return DOCUMENT ME!
     * @throws javax.xml.parsers.ParserConfigurationException DOCUMENT ME!
     * @throws org.xml.sax.SAXException                       DOCUMENT ME!
     * @throws java.io.IOException                            DOCUMENT ME!
     */
    public static Document getDocumentFromXml(String xml) throws ParserConfigurationException, SAXException,
            IOException {
        org.xml.sax.InputSource inStream = new org.xml.sax.InputSource();
        Reader reader = null;
        Document doc = null;

        try {
            reader = new java.io.StringReader(xml);
            inStream.setCharacterStream(reader);
            doc = DOMSupport.db.parse(inStream);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return doc;
    }

    /**
     * DOCUMENT ME!
     *
     * @param node  DOCUMENT ME!
     * @param xPath DOCUMENT ME!
     * @return DOCUMENT ME!
     * @throws javax.xml.xpath.XPathExpressionException DOCUMENT ME!
     */
    public static NodeList selectNodes(Node node, String xPath) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xPathObj = factory.newXPath();
        XPathExpression expr = xPathObj.compile(xPath);
        Object eval = expr.evaluate(node, XPathConstants.NODESET);

        return (NodeList) eval;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     * @throws javax.xml.parsers.ParserConfigurationException DOCUMENT ME!
     */
    public static Document getEmptyDocument() throws ParserConfigurationException {
        return DOMSupport.db.newDocument();
    }

}
