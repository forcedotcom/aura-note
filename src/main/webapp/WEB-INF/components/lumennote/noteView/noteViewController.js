({
    edit: function(component) {
        var note = component.getValue("v.note");

        var event = $L.get("e.lumennote:openNote")
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
            var event = $L.get("e.lumennote:noteAdded");
            event.setParams({noteList : action.getReturnValue()});
            event.fire();

            var event = $L.get("e.lumennote:openNote")
            event.setParams({
                mode : "new",
            });
            event.fire();
        });
        a.runAfter(a);
    }
})