//notation: js file can only use this kind of comments
//since comments will cause error when use in webview.loadurl,
//comments will be remove by java use regexp
(function() {



    window.HAS_HEADER = true;
    var bridge;

    function init(){

    }

    function checkJSBridge(){

        if(window.WebViewJavascriptBridge){

            if(!bridge){
                bridge = window.WebViewJavascriptBridge;
                connectWebViewJavascriptBridge(function(bridge) {
//                        bridge.init(function(message, responseCallback) {
//                            console.log('JS got a message', message);
//                            var data = {
//                                'Javascript Responds': 'true'
//                            };
//
//                            if (responseCallback) {
//                                responseCallback(data);
//                            }
//                        });

                    });
            }



            return true;
        }
        else{
            console.log('WebViewJavascriptBridge false');
            return false;
        }
    }


    function open_loading() {
        console.log("open_loading");

        if(!checkJSBridge()){
            return;
        }

        bridge.callHandler(
            'onloadStart'
            , {'param': ''}
            , function(responseData) {
//                console.log('JS responding with', responseData);
            }
        );
    }

    function close_loading() {
        console.log("close_loading");

        if(!checkJSBridge()){
            return;
        }

        bridge.callHandler(
            'onloadEnd'
            , {'param': ''}
            , function(responseData) {
//                console.log('JS responding with', responseData);
            }
        );
    }

    function show_alert(title, msg) {
        console.log("show_alert");

        if(!checkJSBridge()){
            return;
        }

        var content = new Content();
        content.title = title;
        content.msg = msg;

        bridge.callHandler(
            'showAlert'
            , {'param': content}
            , function(responseData) {
//                console.log('JS responding with', responseData);
            }
        );
    }


    function close(){
        console.log("close");
        if(!checkJSBridge()){
            return;
        }
        bridge.callHandler(
            'close'
            , {'param': ''}
            , function(responseData) {
//                console.log('JS responding with', responseData);
            }
        );
    }

    function showSubmit(visibility) {

        console.log("showSubmit:"+visibility);
        if(!checkJSBridge()){
            return;
        }
        bridge.callHandler(
            'showSubmit'
            , {'param': '124...'}
            , function(responseData) {
//                console.log('JS responding with', responseData);
            }
        );


    }

    function setTitle(title) {
        console.log("setTitle:"+title);
        if(!checkJSBridge()){
            return;
        }
        bridge.callHandler(
            'setTitle'
            , {'param': title}
            , function(responseData) {

            }
        );
    }

    function pendingRefresh(){
        console.log("pendingRefresh");
        if(!checkJSBridge()){
            return;
        }
        bridge.callHandler(
            'pendingRefresh'
            , {'param': ''}
            , function(responseData) {
//                console.log('JS responding with', responseData);
            }
        );

    }

    function open_dialog(){
        console.log("open_dialog");
        if(!checkJSBridge()){
            return;
        }
        bridge.callHandler(
            'openDialog'
            , {'param': ''}
            , function(responseData) {
//                console.log('JS responding with', responseData);
            }
        );

    }

    function close_dialog(){
        console.log("close_dialog");
        if(!checkJSBridge()){
            return;
        }
        bridge.callHandler(
            'closeDialog'
            , {'param': ''}
            , function(responseData) {
//                console.log('JS responding with', responseData);
            }
        );

    }

    function connectWebViewJavascriptBridge(callback) {
        if (window.WebViewJavascriptBridge) {
            callback(WebViewJavascriptBridge)
        } else {
            window.document.addEventListener(
                'WebViewJavascriptBridgeReady'
                , function() {
                    callback(WebViewJavascriptBridge)
                },
                false
            );
        }
    }

    function supmobile_reload(){

        console.log("supmobile_reload");
        if(!checkJSBridge()){
            return;
        }
        bridge.callHandler(
            'supmobileReload'
            , {'param': ''}
            , function(responseData) {
//                console.log('JS responding with', responseData);
            }
        );
    }

        function mobileReload(){

            console.log("mobileReload");
            if(!checkJSBridge()){
                return;
            }
            bridge.callHandler(
                'mobileReload'
                , {'param': ''}
                , function(responseData) {
    //                console.log('JS responding with', responseData);
                }
            );
        }


    function supcon_upload(url, callback, fileName){
        console.log("supcon_upload url:"+url+" callback:"+callback+" fileName:"+fileName);
        if(!checkJSBridge()){
            return;
        }

        window.WebViewJavascriptBridge.callHandler(
            'supconUpload'
            , {'param': url}
            , function(responseData) {
                console.log('supconUpload java responding with', responseData);
                var data = responseData;

                eval(callback, data.split("|")[0], data.split("|")[1]);
            }
        );

    }


    window.mobilejs = {
        init: init,
        checkJSBridge: checkJSBridge,
        showSubmit: showSubmit,
        open_loading: open_loading,
        close_loading: close_loading,
        setTitle: setTitle,
        show_alert: show_alert,
        pendingRefresh: pendingRefresh,
        close: close,
        connectWebViewJavascriptBridge:connectWebViewJavascriptBridge,
        supmobile_reload: supmobile_reload,
        mobileReload: mobileReload,
        open_dialog: open_dialog,
        close_dialog: close_dialog,
        supcon_upload: supcon_upload
    };





})();
