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
    createNote: function(component) {
        var event = $A.get("e.auranote:openNote")
        event.setParams({mode : "new", sort : component.get("v.sort")});
        event.fire();
    },

    sort : function(component) {
        var sortVal = component.find("sort").get('v.value');
        component.set("v.sort", sortVal);
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
                        sort: sortVal
                    }
                }
            }
        );
    },

    noteAdded : function(cmp, event){
        cmp.find("list").set("v.body", event.getParam("noteList"));
    },

    search : function(component, event){
        var sort = component.find("sort").get('v.value');
        var action = $A.get("c.aura://ComponentController.getComponent");
        var query = component.find("searchbox").getElement();

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
                        sort: sort,
                        query: query.value
                    }
                }
            }
        );
    }
})
