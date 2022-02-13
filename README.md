# PhotoOrders

## Assumptions
- We assume that all hours come from the same timezone.
- Uploads do not exist in the data model.
- Zip is uplaoded as base64 binary data but we do nothing with it.
- We can cancel a completed order.
- Only happy path tests using the inmemory DB.