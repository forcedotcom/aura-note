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
            // delete note after test
            var deleter = new (function() {
                this.deleteNote = function() {
                    var deleteButton = cmp.find("details").find("notes").get("v.body")[0].getSuper().get("v.buttons")[1];
                    test.click(deleteButton.getElement());
                }
            })();
            
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
            $P.test.runAfterIf(
                function(){ return buttons[0].getElement() !== null; },
                function(){
                    // check the right buttons are displayed
                    $P.test.assertEquals(2, buttons.length, "expected cancel and save");
                    $P.test.assertEquals("Cancel", buttons[0].find("div").getElement().innerText, "Cancel button not first");
                    $P.test.assertEquals("Save", buttons[1].find("div").getElement().innerText, "Save button not second");

                    // fill in the values and save
                    editCmp.getSuper().get("v.title")[0].getValue("v.value").setValue(title);
                    editCmp.getSuper().get("v.body")[0].getValue("v.value").setValue(body);
                    test.click(buttons[1].getElement());

                    // check view cmp displays; wait for view type to change
                    $P.test.runAfterIf(
                        function(){ return notesCmp.get("v.body")[0].getDef().getDescriptor().getName() == "noteView"; },
                        function(){
                            var details = notesCmp.get("v.body")[0];
                            var buttons = details.getSuper().get("v.buttons");
                            $P.test.assertEquals(2, buttons.length, "expected edit and delete");

                            $P.test.assertEquals("Edit", buttons[0].find("div").getElement().innerText, "Edit button not displayed");
                            $P.test.assertEquals("Delete", buttons[1].find("div").getElement().innerText, "Delete button not displayed");
                            $P.test.assertEquals(title, details.getSuper().get("v.title")[0].getElement().textContent, "wrong title in detail");
                            $P.test.assertEquals(body, details.getSuper().get("v.body")[0].getElement().textContent, "wrong body in detail");
                        }
                    );

                    // check list updated; wait for first list item title to match
                    $P.test.runAfterIf(
                        function(){ var row = list.get("v.body")[0].find("row");  row = row[0] || row; return row && row.getElement().getElementsByClassName("subject")[0].childNodes[0].textContent === title; },
                        function(){
                            var row = list.get("v.body")[0].find("row");
                            row = row[0] || row;
                            $P.test.assertEquals(body, row.getElement().getElementsByClassName("desc")[0].innerText.trim(), "wrong body in list");

                            // must click item in sidebar before deleting due to bug:
                            // W-1381014
                            test.click(row.getElement());
                            deleter.deleteNote(row.getElement());
                        }
                    );
                }
            );
        }
    },
})