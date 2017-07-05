package ru.evtushenko.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.evtushenko.TurnResult;

public class ModelTest {

    private Model model;

    @Before
    public void setUp() throws Exception {
        this.model = new Model();
    }

    @Test
    public void setShape() throws Exception {
        model.setShape(1,2);
        Assert.assertEquals(model.getField()[1][2], Shape.X);
    }

    @Test
    public void gameEnds() throws Exception{
        model.setShape(0,0);
        model.setShape(1,1);
        model.setShape(0,1);
        model.setShape(1,2);
        TurnResult result = model.setShape(0,2);
        Assert.assertEquals(result, TurnResult.X_WON);
    }

}