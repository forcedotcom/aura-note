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
        var note = component.get("v.note");
        var mode = "";
        var origTitle = component.get("v.origTitle");
        var origBody = component.get("v.origBody");
        
        //Check if there was a previous note saved, if not open new note view up, otherwise, look at edit
        if($A.util.isUndefinedOrNull(origBody)){
            mode = "new"; 	
        }
        else{
           mode = "view";
        }

        // Revert title/body text
        note["title"] = origTitle;
        note["body"] = origBody;

        var event = $A.get("e.auranote:openNote")
        event.setParams({
            mode : mode,
            note : note
        });
        event.fire();
    },

    save : function(component){
        var action = component.get("c.saveNote");
        var note = component.get("v.note");
        var sort = component.get("v.sort");

        action.setParams({
            title : note["title"],
            body : note["body"],
            latitude : note["latitude"],
            longitude : note["longitude"],
            id : note["id"],
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
        var note = component.get("v.note");
        var button = component.find("ui_button_set_location");

        button.set("v.label", "Getting Location");

        var success = function(results){
            var latitude = results.coords.latitude;
            var longitude = results.coords.longitude;

            $A.run(function() {
                component.set("v.note.latitude", latitude);
                component.set("v.note.longitude", longitude);

                button.set("v.label", longitude + ", " + latitude);
                button.set("v.disabled", "true");
            });
        };

        var failure = function(results){
            $A.log("Failed to get geocoordinates");
            button.set("v.label", "Failed to get location. Try Again...");
            button.set("v.disabled", "false");
        };

        if(navigator.geolocation){
            //Async call to try and get the location
            navigator.geolocation.getCurrentPosition(success, failure);
        } else {
            failure();
        }
    },
    
    sortChanged : function(component, event){
    	var sortVal = event.getParam("sort");
        component.set("v.sort", sortVal);
    }
})
