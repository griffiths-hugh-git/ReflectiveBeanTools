# ReflectiveBeanTools

Uses reflection to recursively populate beans, for use in unit tests.
This allows developers to assume that all fields are filled out with placeholders, without needing to write boilerplate code to insert values manually, especially for large object graphs.

This is really an intellectual exercise coming out of discussions about mocking strategies, and should not be used seriously.