<#include "header.ftl">
    
    <div class="slider">
            <div class="content">
                <div class="principal">
                    <div class="center text">
                        <label><span>car Sharer</span></label>
                    </div>
	                    <p>Meine reserrvierten Fahrten</p>
	                    <div class="Boxed"> 
	                    	<table>
									
									<tr>
										<pre style="font-size: 14px;font-weight: 900;" ><label>Von    </label><label>Nach  </label><label>Status   </label></pre>
									</tr>
									<tbody>
										<#list reservierteFahrten as fa>
										<tr>
											<td><pre>${fa.von}</pre></td>
											<td><pre>${fa.nach}        </pre></td>
											<td><pre>${fa.status}      </pre></td>
											
											<form action="fahrtdetail" method="get">
												<td><button name = "fid" placeholder="Fid" value =${fa.fid} type="submit">Details</button></td>
											</form>
										
										</tr>
										</#list>
									</tbody>
								</table>
	                    </div>
	
	                    <p>Offene Fahrten</p>
	                    
	                    <div class="Boxed" style="height: 200;"> 
	                    	<table>
	                    			<tr>
										<pre style="font-size: 14px;font-weight: 900;" ><label>startort    </label><label>zielort   </label><label>fahrtkosten   </label><label>freiplatz</label></pre>
									</tr>
										<#list offeneFahrten as of>
										<tr>
											<td><pre>${of.startort}<pre></td>
											<td><pre>${of.zielort}        </pre></td>
											<td><pre>${of.fahrtkosten}            </pre></td>
											<td><pre>${of.freiplatz}      </pre></td>
											
											<form action="fahrtdetail" method="get">
												<td><button name = "fid" placeholder="Fid" value =${of.fid} type="submit">Details</button></td>
											</form>
											
										</tr>
										</#list>
							</table>
	                    </div>
					
					<form action="newDrive" method="get">
                    	<input class = "newride" type="submit" value="Fahrt erstellen" />
					</form>
					<br><br>
					<br><br>
                </div>
            </div>
        </div>

    </div>


    <script src="app.js"></script>
</div>

</body>

</html>