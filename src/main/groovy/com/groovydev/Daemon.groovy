package com.groovydev

import groovy.util.logging.Slf4j

/**
 * Daemon class
 */
@Slf4j
class Daemon {

    static void main(String[] args) {

        log.info "Daemon starting ..."

        def service = new DigestService()
        service.startApplicationContext()

        Thread.currentThread().join()

        log.info "Daemon going down ..."
    }
}
