package sk.fri.uniza.views;

import io.dropwizard.views.View;

import javax.ws.rs.core.UriInfo;
import java.util.List;

public abstract class MaterializePage<H extends PagePart, F extends PagePart> extends View {

    private final String contentTemplate;
    private final H header;
    private final F footer;
    private final UriInfo uriInfo;
    private List<String> cssFiles;
    private List<String> jsFiles;

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
