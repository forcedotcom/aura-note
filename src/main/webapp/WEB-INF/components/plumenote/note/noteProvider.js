({
	provide : function(cmp){
		return cmp.getAttributes().get("mode")==="view"?"plumenote:noteView":"plumenote:noteEdit";
	}
})