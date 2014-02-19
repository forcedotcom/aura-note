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
    edit: function(component) {
        var note = component.getValue("v.note");

        var event = $A.get("e.auranote:openNote")
        event.setParams({
            mode : "edit",
            note : note
        });
        event.fire();
    },

    "delete" : function(component) {
        var a = component.get("c.deleteNote");
        a.setParams({ id : component.get("v.note.id"), sort : component.get("v.sort") });
        a.setCallback(this, function(action){
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
                            sort: component.get("v.sort")
                        }
                    }
                }
            );

            var event = $A.get("e.auranote:openNote")
            event.setParams({
                mode : "new"
            });
            event.fire();
        });
        $A.enqueueAction(a);
    }
})