<#include "header.ftl">

			<div class="slider">
				<div class="content">
					<div class="principal">
						<div class="center text"> <label><span>Fahrt erstellen</span></label> </div>
						<form action="newDrive" method="post">
							<fieldset id = "fahrterstellen">
								<div> <label> Von: </label>  <input type="text" class = "inputrev" name="von" required>  </div>
								<br><br>
								<div> <label> Bis: </label>  <input type="text" class = "inputrev" name="bis" required> </div>
								<br><br>
	
								<div> <label> Maximale Kapazität: </label>  <input type="text" class = "inputrev" name="maxkap" required> </div>
								<br><br>
								<div> <label> Fahrkosten: </label>  <input type="text" class = "inputrev" name="kosten" required>
									<select name="pament" id="payment">
										<option value="Euro">Euro</option>
										<option value="Dolar">Dolar</option>
									</select>
	
								</div>
								<br><br>
								<div> <label> Transportmittel:</label>
								<div   name = "Transportmittel" class="rb">
	                                <div style="color:black;">
	                                    Auto <input class = "inputrev" type="radio" name="Transportmittel"  value="1" checked>
	                                </div>	
		                            <div style="color:black;">
		                                Bus <input class = "inputrev" type="radio" name="Transportmittel"  value="2">
		                            </div>
		                            <div style="color:black;">
		                                Kleintransporter <input class = "inputrev" type="radio" name="Transportmittel"  value="3">
		                            </div>
								</div>
								<br><br>
								
								
								<div>
									<label>Fahruhrzeit:</label>
									<input type="date" class = "inputrev" id="appt" name="datum" min="2022-02-01" max="2023-01-23" required>
									<input type="time" id="appt" name="time" required>
								</div>
								<br><br>
								<br><br>
								<div>
									<label>Beschreibung:</label>
									<textarea rows="5" class = "inputrev" name="beschreibung" cols="37" maxlength="50" id="TITLE" ></textarea>
								</div>
								<br><br>
								<br><br>
								<br><br>
							</fieldset>
							<input class="erstellen" type="submit" value="fahrterstellen" >
						</form>
						
					</div>
				</div>
			</div>


		</div>




		<script src="app.js"></script>
		</div>

	</body>

</html>