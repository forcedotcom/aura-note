/*
 * Copyright (C) 2014 salesforce.com, inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
({
    cancel: function(component, evt, helper) {
        var note = component.getValue("v.note");
        var mode = "";
        var title = component.getValue("m.origTitle").getValue();
        var body = component.getValue("m.origBody").getValue();
        
        //Check if there was a previous note saved, if not open new note view up, otherwise, look at edit
        if($A.util.isUndefinedOrNull(body)){
            mode = "new"; 	
        }
        else{
           mode = "view";
        }

        // Revert title/body text
        note.put("title", title);
        note.put("body", body);

        var event = $A.get("e.auranote:openNote")
        event.setParams({
            mode : mode,
            note : note
        });
        event.fire();
    },

    save : function(component){
        var action = component.get("c.saveNote");
        var note = component.getValue("v.note");
        var sort = component.get("v.sort");
        note.getValue("title").commit();
        note.getValue("body").commit();

        action.setParams({
            title : note.get("title"),
            body : note.get("body"),
            latitude : note.get("latitude"),
            longitude : note.get("longitude"),
            id : note.get("id"),
            sort : sort
        });

        action.setCallback(this, function(a){
            // View the newly created note
            var event = $A.get("e.auranote:openNote")
            event.setParams({
                mode : "view",
                note : a.getReturnValue()
            });
            event.fire();

            // Update sidebar list
            $A.newCmpAsync(
                this,
                function(newCmp){
                    var event = $A.get("e.auranote:noteAdded");
                    event.setParams({noteList : newCmp});
                    event.fire();
                },
                {
                    componentDef : {
                        descriptor: "auranote:noteList"
                    },
                    attributes : {
                        values: {
                            sort: sort
                        }
                    }
                }
            );
        });

        $A.enqueueAction(action);
    },

    setLocation : function(component){
        var note = component.getValue("v.note");

        var cmpAttrb = component.find("ui_button_set_location").getAttributes();
        //Changing the label of the button
        cmpAttrb.setValue("label", "Getting Location");
        var success = function(results){
            note.add("latitude", results.coords.latitude);
            note.add("longitude", results.coords.longitude);
            note.getValue("latitude").setValue(results.coords.latitude);
            note.getValue("longitude").setValue(results.coords.longitude);
            cmpAttrb.setValue("label", note.getValue("longitude").getValue() + ", " + note.getValue("latitude").getValue());
            //Disabling the button
            cmpAttrb.setValue("disabled", "true");
        };

        var failure = function(results){
            $A.log("failure");
            cmpAttrb.setValue("label", "Failed to get location. Try Again...");
            cmpAttrb.setValue("disabled", "false");
        };

        if(navigator.geolocation){
            //Async call to try and get the location
            navigator.geolocation.getCurrentPosition(success, failure);

            // Setting the components visible attribute, changes the value to true and triggers it to be rerendered
            window.setTimeout(function () {
                $A.run(function() {
                    var attributes = component.getAttributes();
                    attributes.setValue('visible', true);
                });
            }, 5000);

            

        } else {
            failure();
        }
    }
})
