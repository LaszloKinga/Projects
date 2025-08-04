package edu.bbte.idde.lkim2156;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class WebshopTemplateServlet {
    private static final Logger LOG = LoggerFactory.getLogger(WebshopTemplateServlet.class);


    private static Handlebars handlebars;

    public static synchronized Template getTemplate(String templateName) throws IOException {

        if (handlebars == null) {
            LOG.info("Building handlebars renderer");


            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/template");
            loader.setSuffix(".hbs");
            handlebars = new Handlebars(loader);
        }

        return handlebars.compile(templateName);
    }
}
