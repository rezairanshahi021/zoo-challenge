package org.iranshahi.zoochallenge.business.service;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.iranshahi.zoochallenge.business.dto.FavouriteRoomDto;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavouriteRoomReportingService {
    private final MongoTemplate mongo;

    public List<FavouriteRoomDto> listFavouriteRooms() {
        var aggregate = Aggregation.newAggregation(
                Aggregation.unwind("favouriteRoomIds"),
                Aggregation.group("favouriteRoomIds").count().as("count"),
                Aggregation.lookup("rooms", "_id", "_id", "roomData"),
                Aggregation.unwind("roomData"),
                Aggregation.project()
                        .and("_id").as("roomId")
                        .and("roomData.title").as("title")
                        .and("count").as("count")
        );
        AggregationResults<Document> results = mongo.aggregate(aggregate, "animals", org.bson.Document.class);
        return results.getMappedResults().stream()
                .map(d ->
                        new FavouriteRoomDto(
                                d.getString("roomId"),
                                d.getString("title"),
                                d.getInteger("count").longValue()
                        )
                ).toList();
    }
}
