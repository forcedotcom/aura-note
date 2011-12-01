({
	rerender : function(cmp){
		var notesValue = cmp.getValue("m.notes");
		if(notesValue.isDirty()){
			notesValue.commit();/*
			var list = cmp.find("list").get("v.body");
			var first = list[0].get("v.note");
			
			notesValue.each(function(note){
				var id = note.id;
				force.log(first, id);
			});*/
		}
	}
})