({
	provide : function(cmp){
		return cmp.getAttributes().get("mode")==="view"?"auranote:noteView":"auranote:noteEdit";
	}
})