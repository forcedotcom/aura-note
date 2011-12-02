<lumen:application preload="lumennote">
  <lumen:handler event="lumennote:noteAdded" action="{!c.noteAdded}" />
  <div>
    <header>
    	<h1>lumen:note</h1>
    </header>
	<ui:block class="wrapper" lumen:id="block">
		<lumen:set attribute="left">
			<lumennote:noteList/>
		</lumen:set>
		<lumennote:noteDetails/>
	</ui:block>
  </div>
</lumen:application>
