({
	noteAdded : function(cmp, event){
		var blockLeft = cmp.find("block").getValue("v.left");
		var noteList = $L.componentService.newComponent(event.getParam("noteList"));
		blockLeft.destroy();
		blockLeft.setValue(noteList);
	}
})