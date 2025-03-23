<#include "header.ftl">
    
    
    <div class="slider">
        <div class="content">
            <div class="principal">
                <div class="center text"> <label><span>Fahrt Bewertung</span></label> </div>
                <form action="rating" method="post">
                    <div class="rb-box">
                    <br><br>
                    <div>
                        <p>Bewertungtext: </p>
                        <textarea rows="5" cols="40" id="TITLE" name= "Bewertungtext"></textarea>
                        <br><br>
                        <br><br>
                        <br><br>

                        <p>Bewertungsrating 1 bis 5:  </p>
                        <div id="rb-1" name = "Bewertungrating" class="rb">
                            <div class="rb-tab" data-value="1">
                                <div class="rb-spot">
                                    1 <input type="radio" name="Bewertungrating"  value="1">
                                </div>
                            </div>
                         	<div class="rb-tab" data-value="2">
	                            <div class="rb-spot">
	                                2 <input type="radio" name="Bewertungrating"  value="2">
	                            </div>
                       		 </div>
	                        <div class="rb-tab" data-value="3">
	                            <div class="rb-spot">
	                                3 <input type="radio" name="Bewertungrating"  value="3">
	                            </div>
                        	</div>
                        	<div class="rb-tab" data-value="4">
                            	<div class="rb-spot">
                                	4 <input type="radio" name="Bewertungrating"  value="4">
                            </div>
                       		 </div>
                        	<div class="rb-tab" data-value="5">
                           		 <div class="rb-spot">
                               		5 <input type="radio" name="Bewertungrating"  value="5">
                           		 </div>
                       		 </div>
                        </div>

                        <div class="button-box">
                        	<button type="submit" name = "Bewertungtext" class="button trigger" value="bewerten">Bewerten</button>
                        </div>
                </form>
            </div>
        </div>
    </div>

</div>


<script src="app.js"></script>
</div>

</body>

</html>