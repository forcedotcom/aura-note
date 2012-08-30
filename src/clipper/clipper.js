var Clipper = {};
Clipper.init = function(){
    var server = localStorage["server"];
    if(!server){
        server = "http://localhost:8080/";
        localStorage["server"] = server;
    }
    Clipper.createMenu();
};

Clipper.getServer = function(){
    return localStorage["server"];
};

Clipper.createMenu = function(){
    var parent = chrome.contextMenus.create({
                                    "title": "Plumenote Web Clipper",
                                    "contexts":["all"]
                                });
    chrome.contextMenus.create({
                                    "title": "New Note",
                                    "contexts":["all"],
                                    "onclick": Clipper.newNote,
                                    "parentId" : parent,
                                    
                                });
    chrome.contextMenus.create({
                                    "title": "Clip URL",
                                    "contexts":["all"],
                                    "onclick": Clipper.clipURL,
                                    "parentId" : parent,
                                    
                                });

    chrome.contextMenus.create({
                                    "title": "Clip Selection",
                                    "contexts":["selection"],
                                    "onclick": Clipper.clipSelection,
                                    "parentId" : parent,
                                    
                                });

    chrome.contextMenus.create({
                                    "title": "Clip Image",
                                    "contexts":["image"],
                                    "onclick": Clipper.clipImage,
                                    "parentId" : parent,
                                    
                                });


};

Clipper.newNote = function(){
    chrome.windows.create({
        url : Clipper.getServer() + "plumenote/noteCreate.app",
        width : 640,
        height: 480
    });
};

Clipper.clipURL = function(data){
    var url = data.linkUrl || data.srcUrl || data.frameUrl || data.pageUrl;    
    chrome.windows.create({
        url : Clipper.getServer() + "plumenote/noteCreate.app?url="+url,
        width : 640,
        height: 480
    });
};

Clipper.clipSelection = function(data){
    var selection = data.selectionText;
    chrome.windows.create({
        url : Clipper.getServer() + "plumenote/noteCreate.app?text="+selection,
        width : 640,
        height: 480
    });
};


Clipper.clipImage = function(data){
    var url = data.srcUrl;
    Clipper.getBase64Image(url, function(image){
        chrome.windows.create({
            url : Clipper.getServer() + "plumenote/noteCreate.app?image="+image,
            width : 640,
            height: 480
        });
    });
};

Clipper.getBase64Image = function (url, callback) {
    var img = document.createElement("img");
    img.style.visiblity="hidden";
    img.src = url;
    
    function process(){
        var canvas = document.createElement("canvas");
        document.body.appendChild(canvas);
        var style = getComputedStyle(img);
        canvas.width = style.width.replace("px","");
        canvas.height = style.height.replace("px","");

        // Copy the image contents to the canvas
        var ctx = canvas.getContext("2d");
        ctx.drawImage(img, 0, 0);

        var dataURL = canvas.toDataURL("image/png");
        document.body.removeChild(img);
        document.body.removeChild(canvas);
        callback(dataURL);
    }
    
    
    img.onload = process;
    document.body.appendChild(img);
}

Clipper.init();
