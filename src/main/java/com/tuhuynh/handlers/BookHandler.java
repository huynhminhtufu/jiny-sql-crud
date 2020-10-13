package com.tuhuynh.handlers;

import com.google.gson.Gson;
import com.jinyframework.core.RequestBinderBase.Context;
import com.jinyframework.core.RequestBinderBase.HttpResponse;
import com.tuhuynh.daos.BookDAO;
import com.tuhuynh.entities.Book;
import com.tuhuynh.utils.ResponseMessage;
import lombok.val;

public class BookHandler {
    private static final Gson gson = new Gson();

    public static HttpResponse listBook(Context ctx) {
        return HttpResponse.of(BookDAO.listBook());
    }

    public static HttpResponse getBook(Context ctx) {
        val id = Integer.parseInt(ctx.pathParam("id"));
        return HttpResponse.of(BookDAO.getBook(id));
    }

    public static HttpResponse createBook(Context ctx) {
        val body = ctx.getBody();
        val newBook = gson.fromJson(body, Book.class);
        val newBookId = BookDAO.createBook(newBook);
        return newBookId != -1 ? HttpResponse.of(new ResponseMessage("Inserted book id: " + newBookId))
                : HttpResponse.of(new ResponseMessage("Failed to insert book")).status(400);
    }

    public static HttpResponse updateBook(Context ctx) {
        val id = Integer.parseInt(ctx.pathParam("id"));
        val body = ctx.getBody();
        val updatedBook = gson.fromJson(body, Book.class);
        val result = BookDAO.updateBook(id, updatedBook);
        return result ? HttpResponse.of(new ResponseMessage("Updated book id: " + id))
                : HttpResponse.of(new ResponseMessage("Failed to update book id: " + id)).status(400);
    }

    public static HttpResponse deleteBook(Context ctx) {
        val id = Integer.parseInt(ctx.pathParam("id"));
        val result = BookDAO.deleteBook(id);
        return result ? HttpResponse.of(new ResponseMessage("Deleted book id: " + id))
                : HttpResponse.of(new ResponseMessage("Failed to delete book id: " + id)).status(400);
    }
}
