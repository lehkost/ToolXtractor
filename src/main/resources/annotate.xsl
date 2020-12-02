<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0" xpath-default-namespace="http://www.tei-c.org/ns/1.0">
    <xsl:output method="xml" encoding="utf-8" indent="yes" />

    <!-- Identity template : copy all text nodes, elements and attributes -->
    <xsl:template match="@*|*" mode="#default">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" />
        </xsl:copy>
    </xsl:template>

    <xsl:template match="@*|*" mode="inside">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" mode="#current" />
        </xsl:copy>
    </xsl:template>

    <xsl:template match="text()" mode="inside">
        <xsl:variable name="vWords" select="tokenize(string(.), '[\s.?!,;—:\-]+') [.]"/>
        <xsl:sequence select="for $current in .,
            $i in 1 to count($vWords)
            return
                if($vWords[$i] eq 'spacy')
                then ('rs', ., 'rs')
                else (.)
        "/>
<!--        <xsl:if test="matches(., '(\bspacy\b)', '!i')">-->
<!--            <rs xmlns="http://www.tei-c.org/ns/1.0">-->
<!--                <xsl:value-of select="."/>-->
<!--            </rs>-->
<!--        </xsl:if>-->
<!--        <xsl:analyze-string select="." regex="(\\bspacy\\b)">-->
<!--            <xsl:matching-substring>-->
<!--                <rs>-->
<!--                    <xsl:value-of select="regex-group(1)"/>-->
<!--                </rs>-->
<!--            </xsl:matching-substring>-->
<!--            <xsl:non-matching-substring>-->
<!--                <whatever/>-->
<!--            </xsl:non-matching-substring>-->
<!--        </xsl:analyze-string>-->
    </xsl:template>

    <xsl:template match="title">
        <xsl:copy>
            <xsl:apply-templates mode="inside" />
        </xsl:copy>
    </xsl:template>
    <xsl:template match="body">
        <xsl:copy>
            <xsl:apply-templates mode="inside" />
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>