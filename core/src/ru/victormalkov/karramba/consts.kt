package ru.victormalkov.karramba

import ru.victormalkov.karramba.model.StaticWall

//const val GEM_SIZE = 24f
const val GEM_SIZE = 128f
const val ORDINARY_GEM_TYPES = 10
const val MAX_BOARD_GEM_COLORS = 5

const val ATLAS_NAME = "zoo"
const val MUSIC = "Woodland Fantasy_0.mp3"
const val SOUND = "jingles_PIZZA00.ogg"
const val SCORE_FONT = "Roboto-BoldItalic.ttf"
const val FONT_CHARS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>"

const val SCORE3 = 1 // * gem count
const val SCORE4 = 1.5 // * gem count
const val SCORE5 = 2 // * gem count

val wallPool = listOf(
        StaticWall("rpgTile159"),
        StaticWall("rpgTile155"),
        StaticWall("rpgTile156"),
        StaticWall("rpgTile160"),
        StaticWall("rpgTile197", "rpgTile177"),
        StaticWall("rpgTile195", "rpgTile175"),
        StaticWall("rpgTile198", "rpgTile178"),
        StaticWall("rpgTile196", "rpgTile176"))

const val TILE_BOTTOM_LEFT = "rpgTile036"
const val TILE_BOTTOM = "rpgTile037"
const val TILE_BOTTOM_RIGHT = "rpgTile038"
const val TILE_LEFT = "rpgTile018"
const val TILE_RIGHT = "rpgTile020"
const val TILE_TOP_LEFT = "rpgTile000"
const val TILE_TOP = "rpgTile001"
const val TILE_TOP_RIGHT = "rpgTile002"
const val TILE_NORMAL = "rpgTile019"

const val TILE_BACKGROUND = "rpgTile052"
