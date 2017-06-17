package utils;

public class Pair<K, V> {
    private final K X;
    private final V Y;

    public static <K, V> Pair<K, V> createPair(K X, V Y) {
        return new Pair<>(X, Y);
    }

    public Pair(K element0, V element1) {
        this.X = element0;
        this.Y = element1;
    }

    public K getX() {
        return X;
    }

    public V getY() {
        return Y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (X != null ? !X.equals(pair.X) : pair.X != null) return false;
        return Y != null ? Y.equals(pair.Y) : pair.Y == null;
    }
}
