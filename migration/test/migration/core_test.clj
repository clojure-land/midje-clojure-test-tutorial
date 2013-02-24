(ns migration.core-test
  (:use clojure.test
        midje.sweet
        migration.core))

(deftest migration
  (testing "Migration produces a new left and right map"
    (is (= {:new-left {} :clashes #{} :new-right {:a 1}}
           (migrate {:a 1} :a {}))))
  (testing "multiple keys can be moved at once"
    (is (= {:new-left {} :clashes #{} :new-right {:a 1 :b 2}}
           (migrate {:a 1, :b 2} :a :b {}))))
  (testing "Duplicates are not migrated. They are retained in the :clashes set."
    (is (= {:new-left {:clash "not moved"} :clashes #{:clash} :new-right {:clash "retained"}}
           (migrate {:clash "not moved"} :clash {:clash "retained"}))))
  (testing "it is not an error if a key to be moved isn't on the left"
    (is (= {:new-left {}, :clashes #{}, :new-right {:b 2}}
           (migrate {:b 2} :a :b {}))))
  (testing "even if there's no key on the left, a clash is still noted"
    (is (= {:new-left {}, :clashes #{:a}, :new-right {:a 2}}
           (migrate {} :a {:a 2})))))
