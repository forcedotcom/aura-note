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