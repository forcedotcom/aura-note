<lumen:application controller="java://org.lumenframework.demo.notes.controllers.NotesController">
	<ui:button label="Create a note" press="{!c.createNote}"/>
	<ui:block>
		<lumen:set attribute="left">
			<lumennote:noteList/>
		</lumen:set>
		Your note body(s) will show up here soon!
	</ui:block>
</lumen:application>
