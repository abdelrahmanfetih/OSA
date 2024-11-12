<#include "header.ftl">
<div class="x-content-wrapper" style="height: 862px;">
   <section class="content-header">
      <div class="container-fluid">
         <div class="row mb-2">
            <div class="col-sm-6 ">
               <h3>Welcome to our All Products Page</h3>
               <p>Here is a list of all Products</p>
            </div>
            <div class="col-sm-6">
            </div>
         </div>
      </div>
   </section>
   <!-- Main content -->
   <section class="content">
      <div class="container-fluid">
         <table id="tblproducts" name ="tblproducts" class="x-table table table-sm table-hover table-bordered table-striped searchable mt-2">
            <thead>
               <tr>
                  <th width="20%" style="text-align: center">ID</th>
                  <th width="20%" style="text-align: center">Name</th>
                  <th width="20%" style="text-align: center">Desctiption</th>
                  <th width="20%" style="text-align: center">Price</th>
                  <th width="20%" style="text-align: center">Quantatity</th>
                   <th width="20%" style="text-align: center">Details</th>
               </tr>
            </thead>
            <tbody>
               <#list products as product >
               <tr role="row">
                  <td style="text-align: center">
                     <span>
                     ${product.getProductID()}
                     </span>
                  </td>
                  <td style="text-align: center">
                     <span>
                     ${product.getProductName()}
                     </span>
                  </td>
                  <td style="text-align: center">
                     <span>
                     ${product.getProductDescription()}
                     </span>
                  </td>
                  <td style="text-align: center">
                     <span>
                  ${product.getPrice()}   
                     </span>
                  </td>
                  <td style="text-align: center">
                     <span>
                     ${product.getProductQuantatity()}
                     </span>
                  </td>
                   <td style="text-align: center">
                     <a href="product_details?pid=${product.getProductID()}"> clickme
                     </a>
                  </td>
               </tr>
               </#list>
            </tbody>
         </table>
      </div>
   </section>
</div>
<#include "footer.ftl">