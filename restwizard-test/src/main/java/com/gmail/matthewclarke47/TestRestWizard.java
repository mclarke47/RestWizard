package com.gmail.matthewclarke47;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

public class TestRestWizard extends Application<HelloWorldConfiguration> {

    public static void main(String... args) throws Exception {
        new TestRestWizard().run(args);
    }

    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().register(new PostResource());
        environment.jersey().register(new GetResource());
        environment.jersey().register(new PutResource());
        environment.jersey().register(new NoMethodPath());
        RestWizard restWizard = new RestWizard(environment.jersey());
        environment.lifecycle().addServerLifecycleListener(restWizard);
        environment.jersey().register(restWizard.getDocResource());
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle<>());
    }
}

class PostObject {

    @JsonProperty("thisThing")
    private String thingParam;

    @JsonProperty("otherParam")
    private String otherParam;

    @JsonProperty("isBoolean")
    private boolean isBoolean;

    @JsonProperty("count")
    private int count;

    public String getThingParam() {
        return thingParam;
    }

    public String getOtherParam() {
        return otherParam;
    }

    public boolean isBoolean() {
        return isBoolean;
    }

    public int getCount() {
        return count;
    }
}


class HelloWorldConfiguration extends Configuration {

}
