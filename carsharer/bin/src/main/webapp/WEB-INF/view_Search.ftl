<#include "header.ftl">

			<div class="slider">
				<div class="content">
					<div class="principal">
						<div class="center text"> <label><span>Fahrtsuchen</span></label> </div>
						<form action="view_Search" method="post">
							<fieldset id = "fahrterstellen">
								<div> <label> Von: </label>  <input type="text" class = "inputrev" name="von" required>  </div>
								<br><br>
								<div> <label> Bis: </label>  <input type="text" class = "inputrev" name="bis" required> </div>
								<br><br>
								<div>
									<label>ab:</label>
									<input type="date" class = "inputrev" id="appt" name="datum" min="2022-02-01" max="2023-01-23" required>
								</div>
							</fieldset>
							
								<input class="erstellen" type="submit" value="suchen" >
						</form>
						