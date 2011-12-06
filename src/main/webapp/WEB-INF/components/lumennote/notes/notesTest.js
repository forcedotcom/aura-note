({
    click : function(elem) {
        var evObj = document.createEvent("Events");
        evObj.initEvent("click", true, false);
        elem.dispatchEvent(evObj);
    },

    /**
     * Create a new note and check that list and detail view is updated.
     */
    testNewNote:{
        test : function(cmp){
            // save some refs
            var test = this;
            var list = cmp.find("sidebar").find("list");

            // new note values
            var title = "title: " + new Date().getTime();
            var body = "body: " + new Date().getTime();

            // click the New Note button
            test.click(cmp.find("sidebar").find("newNote").getElement());

            // wait for buttons to render in the view
            var notesCmp = cmp.find("details").find("notes");
            var editCmp = notesCmp.get("v.body")[0];
            var buttons = editCmp.getSuper().get("v.buttons");
            $F.test.runAfterIf(
                function(){ return buttons[0].getElement() !== null; },
                function(){
                    // check the right buttons are displayed
                    $F.test.assertEquals(2, buttons.length, "expected 2 buttons");
                    $F.test.assertEquals("Cancel", buttons[0].find("div").getElement().innerText, "Cancel button not first");
                    $F.test.assertEquals("Save", buttons[1].find("div").getElement().innerText, "Save button not second");

                    // fill in the values and save
                    editCmp.getSuper().get("v.title")[0].getValue("v.value").setValue(title);
                    editCmp.getSuper().get("v.body")[0].getValue("v.value").setValue(body);
                    test.click(buttons[1].getElement());

                    // check view cmp displays; wait for view type to change
                    $F.test.runAfterIf(
                        function(){ return notesCmp.get("v.body")[0].getDef().getDescriptor().getName() == "noteView"; },
                        function(){
                            var details = notesCmp.get("v.body")[0];
                            var buttons = details.getSuper().get("v.buttons");
                            $F.test.assertEquals(1, buttons.length, "expected 1 button");
                            $F.test.assertEquals("Edit", buttons[0].find("div").getElement().innerText, "Edit button not displayed");
                            $F.test.assertEquals(title, details.getSuper().get("v.title")[0].getElement().textContent, "wrong title in detail");
                            $F.test.assertEquals(body, details.getSuper().get("v.body")[0].find("content").getElement().innerText, "wrong body in detail");
                        }
                    );

                    // check list updated; wait for first list item title to match
                    $F.test.runAfterIf(
                        function(){ return list.get("v.body")[0].find("row")[0].getElement().getElementsByClassName("subject")[0].childNodes[0].textContent === title; },
                        function(){
                            var row = list.get("v.body")[0].find("row")[0].getElement();
                            $F.test.assertEquals(body, row.getElementsByClassName("lumennoteNoteBody")[0].innerText, "wrong body in list");
                        }
                    );
                }
            );
        }
    }
})