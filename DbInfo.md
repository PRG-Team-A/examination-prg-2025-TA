# Database Information

## Tables

### products

| Column Name   | Description                                                                                                                           | Default Value |
|:--------------|:--------------------------------------------------------------------------------------------------------------------------------------|:-------------:|
| product_uuid  | PRIMARY KEY<br/>The UUID indentifying this product                                                                                    |       -       |
| display_name  | The name that should be displayed                                                                                                     |       -       |
| price         | The price of this product. Should be converted to a double                                                                            |      NaN      |
| needs_cooling | ONLY APPLICABLE TO SOME PRODUCTS.<br/>Integer value that should be convert to a boolean. 0 = unset, 1 = no cooling, 2 = needs cooling |       0       |
| expiry_date   | ONLY APPLICABLE TO SOME PRODUCTS.<br/>unix timestamp of when this product is considered to be expired                                 |       -       |

If data is fetched from the db, only the columns that matter for the type of
product should be considered to be valid data. When fetching
all products, the product type should be inferred from given data.

### warehouse

| Column Name  | Description                          | Default Value |
|:-------------|:-------------------------------------|:-------------:|
| warehouse_id | Primary key, Auto increments.        |       -       |
