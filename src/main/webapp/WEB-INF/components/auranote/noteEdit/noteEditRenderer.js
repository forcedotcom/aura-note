({
    afterRender : function(cmp){
        // Save title/body text before edit in case user cancels modifications
        var origTitle = cmp.getValue("v.note").get("title");
        var origBody = cmp.getValue("v.note").get("body");
        cmp.getValue("m.origTitle").setValue(origTitle);
        cmp.getValue("m.origBody").setValue(origBody);

        cmp.find("title").getElement().focus();
        this.superAfterRender();
    }
})