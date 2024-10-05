package server.managers;

import common.actions.Console;
import common.models.StudyGroup;
//import exceptions.NotUniqueIdException;

import java.util.*;
import java.util.stream.Stream;
//import java.util.TreeMap;


/**
 * Класс для работы с коллекцией
 * Методы: добавление, удаление, сортировка, очистка, поиск по id, ...
 */
public class CollectionManager {

    private Stack<StudyGroup> stackCollection;
    private final Date creationDate;

    /**
     * Конструктор класса CollectionManager
     */
    public CollectionManager() {
        stackCollection = new Stack<>();
        creationDate = new Date();
    }

    /**
     * Получает время создания группы
     *
     * @return время создания
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Получает коллекцию групп
     *
     * @return stackCollection коллекция групп
     */
    public Stack<StudyGroup> getStackCollection() {
        return stackCollection;
    }

    /**
     * Устанавливает коллекцию групп
     *
     * @param stackCollection коллекция групп
     */
    public void setCollection(Stack<StudyGroup> stackCollection) {
        this.stackCollection = stackCollection;

    }


    /**
     * Выводит информацию о коллекции
     */
    public String infoAboutCollection() {
        String str = "";
        for (String s : Arrays.asList(("Тип данных: " + stackCollection.getClass().getName() + "\n"), ("Дата инициализации: " + creationDate + "\n"), ("Количество элементов: " + stackCollection.size()))) {
            str += s;
        }
        return str;
    }

    /**
     * Возвращает группу по id
     *
     * @param id id элемента коллекции
     * @return элемент коллекции
     */
    public StudyGroup getById(long id) {
        for (StudyGroup studyGroup : stackCollection) {
            if (Objects.equals(studyGroup.getId(), id)) return studyGroup;
        }
        return null;
    }

    /**
     * Удаляет первый элемент коллекции
     */
    public void removeFirstElementCollection() {
        stackCollection.remove(stackCollection.firstElement());
    }

    /**
     * Удаляет группу из коллекции
     *
     * @param elem удаляемая группа
     */
    public void removeGroup(StudyGroup elem) {
        stackCollection.remove(elem);
    }

    /**
     * Сортирует коллекцию в обратном порядке
     */
    public void reorder() {
        Collections.reverse(stackCollection);
    }

    /**
     * Возвращает размер коллекции
     *
     * @return размер коллекции
     */
    public int getSizeCollection() {
        return stackCollection.size();
    }

    /**
     * Очистка коллекции
     */
    public void clearCollection() {
        stackCollection.clear();
    }

    /**
     * Вывод элементов коллекции
     */
    public void printAllElements() {
        if (stackCollection.isEmpty()) {
            ResponseManager.addln("Коллекция пустая");
        } else {
            ResponseManager.addln("Элементы коллекции: " + stackCollection.size());
            String studyGroupsInString = stackCollection.toString().replaceAll(", StudyGroup", ",\nStudyGroup").replaceAll("\\[", "").replaceAll("]", "");
            ResponseManager.addln(studyGroupsInString);
        }
    }

    /**
     * Метод перемешивает элементы коллекции групп в случайном порядке.
     */
    public void shuffle() {
        Collections.shuffle(stackCollection);
    }


    /**
     * Метод добавляет новую группу в коллекцию
     *
     * @param studyGroup добавляемая группа
     */
    public void addElementToCollection(StudyGroup studyGroup) {
        stackCollection.add(studyGroup);
    }

    /**
     * Метод обновляет элемент коллекции
     *
     * @param oldStudyGroup старая группа, которая будет заменена
     * @param newStudyGroup новая группа
     */
    public void updateElement(StudyGroup oldStudyGroup, StudyGroup newStudyGroup) {
        oldStudyGroup.update(newStudyGroup);
    }

}