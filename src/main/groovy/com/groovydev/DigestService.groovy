package com.groovydev

import grails.spring.BeanBuilder
import groovy.util.logging.Slf4j
import org.apache.camel.component.properties.PropertiesComponent
import org.apache.camel.core.xml.CamelJMXAgentDefinition
import org.apache.camel.spring.CamelContextFactoryBean
import org.springframework.context.ApplicationContext

/**
 *
 */
@Slf4j
class DigestService {

    ApplicationContext applicationContext

    void startApplicationContext() {

        log.debug "Starting application context ..."

        def bb = new BeanBuilder()
        bb.beans {
            xmlns context:"http://www.springframework.org/schema/context"
            // activate annotation bean config
            context.'annotation-config'()
            // scan packages
            context.'component-scan'('base-package': 'com.groovydev')
        }


        bb.beans {
            xmlns context: "http://www.springframework.org/schema/context"

            // activate annotation bean config
            context.'annotation-config'()
            // scan packages
            context.'component-scan'('base-package': 'com.groovydev')

            properties(PropertiesComponent) {
                location = "classpath:rssdigest.properties"
            }

            camelContext(CamelContextFactoryBean) {
                id = 'rss-digest'
                streamCache = 'true'
                packages = ['com.groovydev.route']
                camelJMXAgent = { CamelJMXAgentDefinition camelJMXAgentDefinition ->
                    mbeanObjectDomainName = 'com.groovydev.camel'
                }
            }
        }

        applicationContext = bb.createApplicationContext()
    }

}
