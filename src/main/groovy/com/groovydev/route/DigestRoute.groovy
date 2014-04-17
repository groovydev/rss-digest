package com.groovydev.route

import com.sun.syndication.feed.synd.SyndEntry
import com.sun.syndication.feed.synd.SyndFeed
import org.apache.camel.Exchange
import org.apache.camel.builder.RouteBuilder


class DigestRoute extends RouteBuilder {

    void configure() throws Exception {

        // RSS consume route
        from("properties:rss:{{rss.uri}}?splitEntries=true&throttleEntries=false&filter=true&consumer.delay=60000")
        .marshal().rss()
        .to("file:/tmp/rssstore")

        // Poll stored rss entries every 10min and prepare rss digest
        from("file:/tmp/rssstore?delete=true&maxMessagesPerPoll=1000&scheduler=quartz2&scheduler.cron=*+0/10+*+*+*+?")
        .unmarshal().rss()
        .aggregate().constant(true)
        .completionSize(1000)
        .completionInterval(5000L)
        .groupExchanges()
        .process({ Exchange e ->
            List<Exchange> list = e.in.getBody(List)
            def entries = list.collect {
                SyndFeed feed = it.in.getBody(SyndFeed)
                SyndEntry entry = feed.entries.first()
                def row = [:]
                row.title = entry.title
                row.description = entry.description.value
                row.publishedDate = entry.publishedDate
                row.link = entry.link
                return row
            }
            e.out.body = entries
        })
        .to("mustache:rssdigest.mustache")
        .to("file:/tmp/rssdigest")

    }

}
