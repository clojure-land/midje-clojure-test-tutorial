(ns migration.core)

(defn migrate [& args]
  (let [[source given-keys destination] ( (juxt first #(drop 1 (drop-last %)) last) args)
        keys-to-move (remove (set (keys destination)) given-keys)]
    [(apply dissoc source keys-to-move)
     (merge destination (select-keys source keys-to-move))]))


