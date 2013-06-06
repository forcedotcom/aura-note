({
    createNote: function(component) {
        var event = $A.get("e.auranote:openNote")
        event.setParams({mode : "new", sort : component.get("v.sort")});
        event.fire();
    },

    sort : function(component) {
        var sort = component.find("sort").getElement();
        component.getValue("v.sort").setValue(sort.value);

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
                        sort: sort.value
                    }
                }
            }
        );
    },

    noteAdded : function(cmp, event){
        var list = cmp.find("list").getValue("v.body");
        list.destroy();
        list.setValue(event.getParam("noteList"));
    },

    search : function(component, event){
        var sort = component.find("sort").getElement();
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
                        sort: sort.value,
                        query: query.value
                    }
                }
            }
        );
    }
})
