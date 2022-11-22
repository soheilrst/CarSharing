<#include "header.ftl">
<div class="slider">
				<div class="content">
					<div class="principal" style="max-height: 200;">
						<div class="center text" style=" position:fixed; bottom:300;">
						 <label><span>Information</span></label> </div>

				       <form action="fahrtdetail" method="get">
							<div class="Boxed" style="height: 300;"> 
							<#list FaherObj as fo>
								<tr class= "container" >
									<div><td><label>Anbieter<pre>${fo.anbieter}</pre> </label></td></div>
									<div><td><label>Fahrdatumunduhrzeit<pre>${fo.fahrdatumunduhrzeit}</pre></label></td></div>
									<div><td><label>StartOrt<pre>${fo.von}</pre></label></td></div>
									<div><td><label>ZielOrt<pre>${fo.nach}</pre></label></td></div>
									<div name = "anzahlfreieplaetze" placeholder="Anzahlfreieplaetze" value =${fo.anzahlfreieplaetze} ><td><label>Freieplaetze<pre>${fo.anzahlfreieplaetze}</pre></label></td></div>
									<div><td><label>Fahrtkosten<pre>${fo.fahrtkosten}</pre></label></td></div>
									<div><td><label>Status<pre>${fo.status}</pre></label></td></div>
									<div><td><label>Beschreibung<pre>${fo.beschreibung}</pre></label></td></div>
								</tr>
							</#list>
							</div>
						</form>		
								
						<form action="fahrtdetail" method="POST">		
							<div class="Boxed"> 
								    
								    <fieldset>
								      <legend>Aktionsliste</legend>
								      	<label>Anzahl plätze für Reservierung: </label>
								     	<form action="fahrtdetail" method="Post">
								     			<input type="NUMBER" MIN="1" MAX="2" STEP="1" size="1" name = "anzahl" value = "1">
										     	<input class="mybutton" type="submit" name="fahrtTry" value="Fahrtreservieren" href ="/view_Main"/>
										     	<input class="mybutton" type="submit" name="fahrtTry" value="FahrtLöschen" href ="/view_Main"/>
								     	</form>
								    </fieldset>
							</div>
						</form>	
						
						<form action="fahrtdetail" method="get">
							<div class="Boxed"> 
							 <legend>Bewertung: </legend>
							<pre><label style="position:absolute; right:100;">Durchschnitt   ${durchschnitt}</label></pre>
							<br></br>
							<fieldset>
								 
								<tr>
									<pre><label>user              </label><label>comment             </label>rating</pre>
								</tr>
								
								
								<#list Rating as r>
									<tr class= "container">
										<div><pre><th> <label>${r.email}</label> <label style="white-space: nowrap;overflow: hidden;text-overflow: ellipsis;max-width: 20ch; display:inline-block">${r.textnachricht}</label> <label style="position:absolute; right:100;">${r.rating}</label></th></pre></div>
									</tr>
								</#list>
							</fieldset>
							
						</form>	  
						<form method="POST" action="fahrtdetail">
							 <input type="submit" name="fahrtTry" value="ratings"/>
						</form>	
							
						
						
					</div>
				</div>
		</div>
		</div>
	</body>
</html>