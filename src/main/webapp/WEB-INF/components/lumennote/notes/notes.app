<lumen:application preload="lumennote" >
  <div>
    <header>
    	<ui:block>
	        <h1>lumen:note</h1>
    		<lumen:set attribute="right">
	        	<ui:button lumen:id="newNote" label="New Note" press="{!c.createNote}" />
    		</lumen:set>
    	</ui:block>
    </header>
    <ui:block class="wrapper" lumen:id="block">
        <lumen:set attribute="left">
            <lumennote:sidebar lumen:id="sidebar"/>
        </lumen:set>
        <lumennote:details lumen:id="details"/>
    </ui:block>
  </div>
</lumen:application>
