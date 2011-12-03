<lumen:application preload="lumennote" >
  <div>
    <header>
        <h1>lumen:note</h1>
    </header>
    <ui:block class="wrapper" lumen:id="block">
        <lumen:set attribute="left">
            <lumennote:sidebar lumen:id="sidebar"/>
        </lumen:set>
        <lumennote:details lumen:id="details"/>
    </ui:block>
  </div>
</lumen:application>
