<#include "header.ftl">

<div class="x-content-wrapper" style="height: 862px;">
   <section class="content-header">
      <div class="container-fluid">
         <div class="row mb-2">
            <div class="col-sm-6 ">
               <h3>Product Details Page</h3>
               <p>Here you can find all details to a specific product:  ${product.getProductName()} </p>
            </div>
            <div class="col-sm-6">
            </div>
         </div>
     
      
   </section>
   <!-- Main content -->
   <section class="content">
      <div class="container-fluid">
         <div class="row">
         <form action="product_details?pid=${product.getProductID()}" method="POST" name="formAddProduct">
         		<div id="productId">ProductId: ${product.getProductID()}</div>
         		<div id="productName">Product Name: ${product.getProductName()}</div>
         		<div id="productQuant">Product Qunatatiy: ${product.getProductQuantatity()}</div>
         		<div id="productPrice">Product Price: ${product.getPrice()}</div>
         		<div id="productDESC">Product description: ${product.getProductDescription()}</div>
         		<br>
    			<input type="submit" value="Add To ShoppingCart" name="registerButton" id="registerButton"/>
		</form>
         
         </div>
      </div>
   </section>
</div>
<#include "footer.ftl">