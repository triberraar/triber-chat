'use strict';

var httpProxy = require('http-proxy');
var express = require('express');
var http      = require( 'http' );
var path = require('path');
var bunyan = require('bunyan');
var logger = bunyan.createLogger({name: "proxy"});

var proxy = httpProxy.createProxyServer({
    target: 'http://127.0.0.1:8080' // where do we want to proxy to?
    //ws    : true // proxy websockets as well
});

var app = express();
app.use(express.static(path.join(__dirname, 'src', 'main', 'resources', 'static')));

var proxyServer = http.createServer( app );

proxyServer.on( 'upgrade', function( req, socket, head ) {
    logger.info({name: 'upgrading websocket'});
    proxy.ws( req, socket, head );
});

proxy.on( 'error', function ( err ) {
    // console.error( err.stack );
   logger.error({name: 'proxy error', err:err});
});

proxy.on( 'proxyReq', function ( proxyReq, req, res ) {
    logger.info({name: 'proxyReq', path: proxyReq.path});
});

proxy.on( 'proxyReqWs', function ( proxyReqWs, req, res ) {
    logger.info({name: 'proxyReq WS', path: proxyReqWs.path});
});

app.all('/*',  function (req, res) {
    proxy.web(req, res, {target: {host: '127.0.0.1', port:'8080'}}, function (e, req, res) {
        console.log(2);
        res.json({'message': 'proxy error'});
    });
});


proxyServer.listen(3333);

logger.info({message: 'listening on 3333'});
