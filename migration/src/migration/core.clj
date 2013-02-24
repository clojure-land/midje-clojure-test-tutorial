(ns migration.core)

(defn migrate [& args]
  (let [[source given-keys destination] ( (juxt first #(drop 1 (drop-last %)) last) args)
        [clash-keys keys-to-move] ((juxt filter remove) (set (keys destination)) given-keys)]
    {:new-left (apply dissoc source keys-to-move)
     :clashes (set clash-keys)
     :new-right (merge destination (select-keys source keys-to-move))}))

   
