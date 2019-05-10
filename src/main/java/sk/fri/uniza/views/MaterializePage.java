package sk.fri.uniza.views;

import io.dropwizard.views.View;

import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * Abstract class that creates basic HTML page template. This template consist of three parts: Header,Body,Footer. <br> This basic template loads CSS and JS files,
 * that are defined in materialize_page.ftl or on runtime by cssFiles,jsFiles lists.
 * @param <H> Header type
 * @param <F> Footer type
 */

public abstract class MaterializePage<H extends PagePart, F extends PagePart> extends View {

    private final String contentTemplate;
    private final H header;
    private final F footer;
    private final UriInfo uriInfo;
    private List<String> cssFiles;
    private List<String> jsFiles;

    /**
     *
     * @param contentTemplate Template file of Body part(represents main content)
     * @param uriInfo uri info from current location
     * @param header Instance of header page part
     * @param footer Instance of footer page part
     */
    protected MaterializePage(String contentTemplate, UriInfo uriInfo, H header, F footer) {
        super("materialize_page.ftl");
        this.contentTemplate = contentTemplate;
        this.uriInfo = uriInfo;
        this.header = header;
        this.footer = footer;
    }

    public UriInfo getUriInfo() {
        return uriInfo;
    }

    public String getCurrentUrl() {
        return uriInfo.getPath();
    }

    public List<String> getCssFiles() {
        return cssFiles;
    }

    public void setCssFiles(List<String> cssFiles) {
        this.cssFiles = cssFiles;
    }

    public List<String> getJsFiles() {
        return jsFiles;
    }

    public void setJsFiles(List<String> jsFiles) {
        this.jsFiles = jsFiles;
    }

    public H getHeader() {
        return header;
    }

    public F getFooter() {
        return footer;
    }

    public String getContentTemplate() {
        return contentTemplate;
    }
}
