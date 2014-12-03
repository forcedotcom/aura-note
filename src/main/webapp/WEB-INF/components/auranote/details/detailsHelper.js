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
    open : function(component, note, mode, sort){
        if(mode === "new"){

            var text = component.get("v.text");
            var image = component.get("v.image");
            if(image){
                text = '<img src="'+image+'"/>';
            }
            var url = component.get("v.url");
            if(url){
                text = '<a href="'+url+'">'+url+'</a>';
            }

            note = $A.expressionService.create(null, {title : "", body : text, latitude : null, longitude: null})
        }

        $A.newCmpAsync(
            this,
            function(newCmp){
                var content = component.find("notes");
                content.set("v.body", newCmp)

                setTimeout(function() {
                    // Let the world know that we need resize calcs
                    $A.get("e.ui:updateSize").fire();
                }, 400);
            },
            {
                componentDef: {
                    descriptor: "markup://auranote:note"
                },
                attributes: {
                    values: {
                        note : note,
                        mode : mode,
                        sort : sort
                    }
                }
            }
        );
    }
})
