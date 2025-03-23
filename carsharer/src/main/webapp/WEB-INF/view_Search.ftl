<#include "header.ftl">

			<div class="slider">
				<div class="content">
					<div class="principal">
						<div class="center text"> <label><span>Fahrt Suchen</span></label> </div>
						<form action="view_Search" method="post">
							<fieldset id = "fahrterstellen">
								<div> <label> Von: </label>  <input type="text" class = "inputrev" name="von" list="cityList" required oninput="capitalizeFirstLetter(this)" >  </div>
								<br><br>
								<div> <label> Bis: </label>  <input type="text" class = "inputrev" name="bis" list="cityList" required oninput="capitalizeFirstLetter(this)"> </div>
								<br><br>
								<div>
									<label>Ab:</label>
									<input type="date" class = "inputrev" id="appt" name="datum"  required>
								
								</div>
							</fieldset>
							
								<input class="erstellen" type="submit" value="suchen" >
						</form>
<datalist id="cityList">
    <option value="Berlin">
    <option value="München">
    <option value="Hamburg">
    <option value="Köln">
    <option value="Frankfurt">
    <option value="Stuttgart">
    <option value="Düsseldorf">
    <option value="Dortmund">
    <option value="Essen">
    <option value="Bremen">
</datalist>

<script>
    
    document.addEventListener("DOMContentLoaded", function() {
        let today = new Date().toISOString().split('T')[0];
        document.getElementById("datum").setAttribute("min", today);
    });

    
    function capitalizeFirstLetter(input) {
        input.value = input.value.charAt(0).toUpperCase() + input.value.slice(1);
    }

    
    document.getElementById("appt").addEventListener("input", function() {
        let now = new Date();
        let selectedDate = document.getElementById("datum").value;
        let selectedTime = this.value;
        
        let selectedDateTime = new Date(selectedDate + 'T' + selectedTime);
        if (selectedDateTime < now) {
            alert("Die gewählte Zeit muss in der Zukunft liegen.");
            this.value = "";
        }
    });
</script>						