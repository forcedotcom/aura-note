({
	finish : function(cmp){
		cmp.getValue("v.message").setValue("Saved.");
		setTimeout(window.close, 800);
	}
})